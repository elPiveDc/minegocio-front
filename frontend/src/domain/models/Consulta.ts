export interface Consulta {
  id: number;
  usuarioId: number;
  tipo: string;
  descripcion: string;
  respuesta?: string;
  fechaCreacion: Date;
  estado: string;
}
