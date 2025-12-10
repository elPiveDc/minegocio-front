import type {
  FranquiciaRequestDTO,
  FranquiciaResponseDTO,
} from "../../../infrastructure/api/dto/Franquicia.dto";

export interface UseFranquiciaResult {
  registrarFranquicia: (payload: FranquiciaRequestDTO) => Promise<string>;
  actualizar: (
    id: number,
    dto: FranquiciaRequestDTO
  ) => Promise<FranquiciaResponseDTO>;
  obtenerPorId: (id: number) => Promise<FranquiciaResponseDTO>;
  obtenerPorSlug: (slug: string) => Promise<FranquiciaResponseDTO>;
  listarPorUsuario: (idUsuario: number) => Promise<FranquiciaResponseDTO[]>;
  eliminar: (id: number) => Promise<void>;
  validarNombre: (nombre: string) => Promise<boolean>;
  validarSlug: (slug: string) => Promise<boolean>;
  cambiarEstado: (id: number, estado: string) => Promise<FranquiciaResponseDTO>;

  isLoading: boolean;
  error: string | null;
  data:
    | string
    | FranquiciaResponseDTO
    | FranquiciaResponseDTO[]
    | boolean
    | null;
}
