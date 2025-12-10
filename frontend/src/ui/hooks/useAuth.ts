import { useState, useCallback, useEffect } from "react";
import type {
  LoginRequest,
  MeResponse,
  RegisterRequest,
} from "../../infrastructure/api/types/auth";
import { hasBackendErrorMessage } from "../../utils/TypeGuards";
import { authService } from "../../infrastructure/services/authServices";

interface AuthState {
  user: MeResponse | null;
  token: string | null;
  isLoading: boolean;
  error: string | null;
}

const getInitialState = (): AuthState => {
  const initialToken = localStorage.getItem("token");
  return {
    user: null,
    token: initialToken,
    isLoading: !!initialToken,
    error: null,
  };
};

export const useAuth = () => {
  const [state, setState] = useState<AuthState>(getInitialState);
  const { token, user } = state;

  const handleApiError = (error: unknown) => {
    let errorMessage = "Ocurrió un error desconocido";

    if (hasBackendErrorMessage(error)) {
      errorMessage = error.response.data.message;
    } else if (error instanceof Error) {
      errorMessage = error.message;
    }

    setState((prevState) => ({
      ...prevState,
      error: errorMessage,
      isLoading: false,
    }));
    return errorMessage;
  };

  const login = useCallback(async (payload: LoginRequest) => {
    setState((prevState) => ({ ...prevState, isLoading: true, error: null }));
    try {
      const { token: newToken, usuario } = await authService.login(payload);

      localStorage.setItem("token", newToken);

      setState({
        token: newToken,
        user: usuario,
        isLoading: false,
        error: null,
      });
      return usuario;
    } catch (error) {
      handleApiError(error);
      throw error;
    }
  }, []);

  const logout = useCallback(async () => {
    localStorage.removeItem("token");
    setState({
      user: null,
      token: null,
      isLoading: false,
      error: null,
    });
  }, []);

  const register = useCallback(async (payload: RegisterRequest) => {
    setState((prevState) => ({ ...prevState, isLoading: true, error: null }));
    try {
      const userData = await authService.register(payload);

      setState((prevState) => ({
        ...prevState,
        isLoading: false,
        error: null,
      }));
      return userData;
    } catch (error) {
      handleApiError(error);
      throw error;
    }
  }, []);

  useEffect(() => {
    const fetchMe = async () => {
      if (token && !user) {
        setState((prevState) => ({
          ...prevState,
          isLoading: true,
          error: null,
        }));
        try {
          const userData = await authService.me();
          setState((prevState) => ({
            ...prevState,
            user: userData,
            isLoading: false,
          }));
        } catch (error) {
          console.error("Error al cargar el perfil. Eliminando token:", error);
          localStorage.removeItem("token");
          setState({
            user: null,
            token: null,
            isLoading: false,
            error: "Sesión expirada o inválida.",
          });
        }
      } else if (!token && state.isLoading) {
        setState((prevState) => ({ ...prevState, isLoading: false }));
      }
    };

    fetchMe();
  }, [token, user, state.isLoading]);

  return {
    ...state,
    isAuthenticated: !!token,
    login,
    logout,
    register,
  };
};
