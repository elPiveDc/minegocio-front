import { api } from "../api/api";
import type {
  LoginRequest,
  LoginResponse,
  RegisterRequest,
  RegisterResponse,
  MeResponse,
  LogoutResponse,
} from "../api/types/auth";

export const authService = {
  //Inicia sesión de un usuario.
  async login(payload: LoginRequest): Promise<LoginResponse> {
    const response = await api.post<LoginResponse>(
      "/auth/publicos/login",
      payload
    );
    return response.data;
  },
  //Registra un nuevo usuario.
  async register(payload: RegisterRequest): Promise<RegisterResponse> {
    const response = await api.post<RegisterResponse>(
      "/auth/publicos/register",
      payload
    );
    return response.data;
  },
  //
  async me(): Promise<MeResponse> {
    const response = await api.get<MeResponse>("/auth/usuario/me");
    return response.data;
  },

  //cierra la sesion
  async logout(): Promise<LogoutResponse> {
    const response = await api.post<LogoutResponse>("/auth/usuario/logout", {});
    return response.data;
  },
};
