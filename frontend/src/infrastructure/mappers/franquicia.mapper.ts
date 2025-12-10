import type { FranquiciaResponseDto } from "../api/dto/Franquicia.dto";
import type { Franquicia } from "../../domain/models/Franquicia";

export const mapFranquiciaDtoToDomain = (
  dto: FranquiciaResponseDto
): Franquicia => ({
  id: dto.id,
  nombre: dto.nombreFranquicia,
  slug: dto.slug,
  descripcion: dto.descripcion,
  estado: dto.estado as Franquicia["estado"],
  fechaCreacion: new Date(dto.fechaCreacion),
  templateId: dto.templateId,
});
