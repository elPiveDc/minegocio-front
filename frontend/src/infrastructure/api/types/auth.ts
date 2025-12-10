export interface LoginRequest {
  correo: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  usuario: {
    id: number;
    nombre: string;
    correo: string;
    rol: string;
  };
}

export interface RegisterRequest {
  nombre: string;
  correo: string;
  password: string;
}

export interface RegisterResponse {
  id: number;
  correo: string;
  nombre: string;
  rol: string;
}

export interface MeResponse {
  id: number;
  nombre: string;
  correo: string;
  rol: string;
}

export interface LogoutResponse {
  message: string;
}

export interface MeCompletoResponse {
  nombre: string;
  correo: string;
  emailVerificado: boolean;
  fechaRegistro: string;
  estado: "ACTIVO" | "INACTIVO" | "SUSPENDIDO" | string;
  avatarUrl: string | null;
  zonaHoraria: string | null;
  lastLogin: string | null;
}
