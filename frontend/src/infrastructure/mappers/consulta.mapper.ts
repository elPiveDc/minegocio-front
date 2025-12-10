import type { ConsultaResponseDto } from "../api/dto/FaqDocumentoConsulta.dto";
import type { Consulta } from "../../domain/models/Consulta";

export const mapConsultaDtoToDomain = (dto: ConsultaResponseDto): Consulta => ({
  id: dto.id,
  usuarioId: dto.idUsuario,
  tipo: dto.tipoConsulta,
  descripcion: dto.descripcion,
  respuesta: dto.respuesta ?? undefined,
  fechaCreacion: new Date(dto.fechaCreacion),
  estado: dto.estado,
});
