import type { MeResponse } from "../../../infrastructure/api/types/auth";

export interface AuthState {
  user: MeResponse | null;
  token: string | null;
  isLoading: boolean;
  error: string | null;
}
