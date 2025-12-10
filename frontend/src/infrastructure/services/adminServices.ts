import type { Consulta } from "../../domain/models/Consulta";
import type { Faq } from "../../domain/models/Faq";
import type { Franquicia } from "../../domain/models/Franquicia";
import { api } from "../api/api";
import type {
  ConsultaEstadoDto,
  ConsultaResponseDto,
  ConsultaRespuestaDto,
  ConsultaUpdateDto,
  DocumentoMetadataResponseDto,
  DocumentoRequestDto,
  FaqRequestDto,
  FaqResponseDto,
} from "../api/dto/FaqDocumentoConsulta.dto";
import type { FranquiciaResponseDTO } from "../api/dto/Franquicia.dto";
import { mapConsultaDtoToDomain } from "../mappers/consulta.mapper";
import { mapFaqDtoToDomain } from "../mappers/faq.mapper";
import { mapFranquiciaDtoToDomain } from "../mappers/franquicia.mapper";

export const adminService = {
  // ------------------- FAQ ----------------------
  crearFaq: async (payload: FaqRequestDto) => {
    const { data } = await api.post<FaqResponseDto>("/admin/faqs", payload);
    return data;
  },

  obtenerFaqs: async (): Promise<Faq[]> => {
    const { data } = await api.get<FaqResponseDto[]>("/publicos/faqs");
    return data.map(mapFaqDtoToDomain);
  },
  actualizarFaq: async (id: number, payload: FaqRequestDto) => {
    const { data } = await api.put<FaqResponseDto>(
      `/admin/faqs/${id}`,
      payload
    );
    return data;
  },

  eliminarFaq: async (id: number) => {
    await api.delete(`/admin/faqs/${id}`);
  },

  // ------------------- DOCUMENTOS ----------------------
  crearDocumento: async (payload: DocumentoRequestDto) => {
    const response = await api.post("/admin/documentos", payload);
    return response.data;
  },

  actualizarDocumento: async (id: number, payload: DocumentoRequestDto) => {
    const response = await api.put(`/admin/documentos/${id}`, payload);
    return response.data;
  },

  eliminarDocumento: async (id: number) => {
    await api.delete(`/admin/documentos/${id}`);
  },

  obtenerDocumentos: async () => {
    const { data } = await api.get<DocumentoMetadataResponseDto[]>(
      "/admin/documentos/todos"
    );
    return data;
  },

  // ------------------- FRANQUICIAS ----------------------
  listarFranquicias: async (): Promise<Franquicia[]> => {
    const { data } = await api.get<FranquiciaResponseDTO[]>(
      "/admin/listar-franquicias"
    );
    return data.map(mapFranquiciaDtoToDomain);
  },
  // ------------------- CONSULTAS (ADMIN) ----------------------
  obtenerTodasConsultas: async (): Promise<Consulta[]> => {
    const { data } = await api.get<ConsultaResponseDto[]>("/admin/consultas");
    return data.map(mapConsultaDtoToDomain);
  },

  obtenerConsultasPorEstado: async (estado: string) => {
    const { data } = await api.get<ConsultaResponseDto[]>(
      `/admin/consultas/estado/${estado}`
    );
    return data;
  },

  actualizarConsulta: async (id: number, payload: ConsultaUpdateDto) => {
    const { data } = await api.put<ConsultaResponseDto>(
      `/admin/consultas/${id}`,
      payload
    );
    return data;
  },

  cambiarEstadoConsulta: async (id: number, payload: ConsultaEstadoDto) => {
    const { data } = await api.put<ConsultaResponseDto>(
      `/admin/consultas/${id}/estado`,
      payload
    );
    return data;
  },

  responderConsulta: async (id: number, payload: ConsultaRespuestaDto) => {
    const { data } = await api.put<ConsultaResponseDto>(
      `/admin/consultas/${id}/responder`,
      payload
    );
    return data;
  },

  eliminarConsulta: async (id: number) => {
    await api.delete(`/admin/consultas/${id}`);
  },
};
