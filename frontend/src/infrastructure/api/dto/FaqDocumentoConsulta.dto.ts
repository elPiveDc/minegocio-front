// -------------------- FAQ Interfaces --------------------
export interface FaqRequestDto {
  question: string;
  answer: string;
}

export interface FaqResponseDto {
  id: number;
  question: string;
  answer: string;
  createdAt: string;
  updatedAt: string;
}

// -------------------- DOCUMENTO Interfaces --------------------

export interface DocumentoRequestDto {
  titulo: string;
  slug: string;
  tipoContenido: string;
  archivo: string;
}

export interface DocumentoMetadataResponseDto {
  id: number;
  titulo: string;
  slug: string;
  tipoContenido: string;
  fechaSubida: string;
}

export interface DocumentoResponseDto {
  id: number;
  titulo: string;
  slug: string;
  tipoContenido: string;
  archivo: string;
  fechaSubida: string;
}

// ----------------------- Consulas -------------------

export interface ConsultaCreateDto {
  idUsuario: number;
  tipoConsulta: "SOPORTE" | "PREGUNTA" | "RECLAMO" | "SUGERENCIA" | string;
  descripcion: string;
}

export interface ConsultaUpdateDto {
  tipoConsulta: "SOPORTE" | "PREGUNTA" | "RECLAMO" | "SUGERENCIA" | string;
  descripcion: string;
}

export interface ConsultaRespuestaDto {
  respuesta: string;
}

export interface ConsultaEstadoDto {
  estado: "PENDIENTE" | "EN_PROCESO" | "RESPONDIDA" | "CERRADA" | string;
}

export interface ConsultaResponseDto {
  id: number;
  idUsuario: number;

  tipoConsulta: "SOPORTE" | "PREGUNTA" | "RECLAMO" | "SUGERENCIA" | string;
  descripcion: string;
  respuesta: string | null;

  fechaCreacion: string;

  estado: "PENDIENTE" | "EN_PROCESO" | "RESPONDIDA" | "CERRADA" | string;
}
