import type { FaqResponseDto } from "../api/dto/FaqDocumentoConsulta.dto";
import type { Faq } from "../../domain/models/Faq";

export const mapFaqDtoToDomain = (dto: FaqResponseDto): Faq => ({
  id: dto.id,
  question: dto.question,
  answer: dto.answer,
  createdAt: new Date(dto.createdAt),
});
