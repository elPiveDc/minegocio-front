export interface Franquicia {
  id: number;
  nombre: string;
  slug: string;
  descripcion?: string;
  estado: "ACTIVO" | "INACTIVO" | "SUSPENDIDO";
  fechaCreacion: Date;
  templateId?: number;
}
