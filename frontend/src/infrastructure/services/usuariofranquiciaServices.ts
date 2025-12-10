import { api } from "../api/api";
import type {
  FranquiciaRequestDTO,
  FranquiciaResponseDTO,
} from "../api/dto/Franquicia.dto";

const BASE = "/usuarios/franquicia";

export interface Usuario {
  id: number;
  nombre: string;
  rol: string;
  email: string;
}

export const franquiciaService = {
  // ----------------------------------------------------
  // CREAR
  // ----------------------------------------------------
  async registrarFranquicia(payload: FranquiciaRequestDTO): Promise<string> {
    const res = await api.post<string>(`${BASE}`, payload);
    return res.data;
  },

  // ----------------------------------------------------
  // ACTUALIZAR
  // ----------------------------------------------------
  actualizar: async (
    id: number,
    data: FranquiciaRequestDTO
  ): Promise<FranquiciaResponseDTO> => {
    const res = await api.put(`${BASE}/${id}`, data);
    return res.data;
  },

  // ----------------------------------------------------
  // OBTENER POR ID
  // ----------------------------------------------------
  obtenerPorId: async (id: number): Promise<FranquiciaResponseDTO> => {
    const res = await api.get(`${BASE}/${id}`);
    return res.data;
  },

  // ----------------------------------------------------
  // OBTENER POR SLUG
  // ----------------------------------------------------
  obtenerPorSlug: async (slug: string): Promise<FranquiciaResponseDTO> => {
    const res = await api.get(`${BASE}/slug/${slug}`);
    return res.data;
  },

  // ----------------------------------------------------
  // LISTAR FRANQUICIAS POR ID USUARIO
  // ----------------------------------------------------
  listarPorUsuario: async (
    idUsuario: number
  ): Promise<FranquiciaResponseDTO[]> => {
    const res = await api.get(`${BASE}/listfranquicia/${idUsuario}`);
    return res.data;
  },

  listarUsuariosPorFranquicia: async (
    idFranquicia: number
  ): Promise<Usuario[]> => {
    const res = await api.get<Usuario[]>(`${BASE}/franquicia/${idFranquicia}`);
    return res.data;
  },

  // ----------------------------------------------------
  // ELIMINAR
  // ----------------------------------------------------
  eliminar: async (id: number): Promise<void> => {
    await api.delete(`${BASE}/${id}`);
  },

  // ----------------------------------------------------
  // VALIDAR NOMBRE (BOOLEAN)
  // ----------------------------------------------------
  validarNombre: async (nombre: string): Promise<boolean> => {
    const res = await api.get(`${BASE}/validar/nombre`, {
      params: { nombre },
    });
    return res.data;
  },

  // ----------------------------------------------------
  // VALIDAR SLUG (BOOLEAN)
  // ----------------------------------------------------
  validarSlug: async (slug: string): Promise<boolean> => {
    const res = await api.get(`${BASE}/validar/slug`, {
      params: { slug },
    });
    return res.data;
  },

  // ----------------------------------------------------
  // CAMBIAR ESTADO
  // ----------------------------------------------------
  cambiarEstado: async (
    id: number,
    estado: string
  ): Promise<FranquiciaResponseDTO> => {
    const res = await api.patch(`${BASE}/${id}/estado`, null, {
      params: { estado },
    });
    return res.data;
  },
};
