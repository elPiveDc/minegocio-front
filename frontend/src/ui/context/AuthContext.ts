import { createContext } from "react";
import type { MeResponse } from "../../infrastructure/api/types/auth";
import { useAuth as useAuthHook } from "../hooks/useAuth";

export interface AuthContextType {
  user: MeResponse | null;
  token: string | null;
  isLoading: boolean;
  error: string | null;
  isAuthenticated: boolean;
  login: ReturnType<typeof useAuthHook>["login"];
  logout: ReturnType<typeof useAuthHook>["logout"];
  register: ReturnType<typeof useAuthHook>["register"];
}

export const AuthContext = createContext<AuthContextType | null>(null);
