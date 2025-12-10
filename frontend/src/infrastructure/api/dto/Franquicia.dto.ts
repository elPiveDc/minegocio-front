export interface FranquiciaRequestDTO {
  nombreFranquicia: string;
  slug: string;
  descripcion?: string;
  templateId?: number;
}

export interface FranquiciaResponseDTO {
  id: number;
  nombreFranquicia: string;
  slug: string;
  descripcion?: string;
  fechaCreacion: string;
  estado: "ACTIVO" | "INACTIVO" | "SUSPENDIDO" | string;
  templateId?: number;
}
