import { api } from "../api/api";
import type {
  DocumentoResponseDto,
  FaqResponseDto,
} from "../api/dto/FaqDocumentoConsulta.dto";

export class PublicosService {
  // -------------------- FAQ Endpoints --------------------

  async obtenerTodasFaqs(): Promise<FaqResponseDto[]> {
    const { data } = await api.get<FaqResponseDto[]>("/publicos/faqs");
    return data;
  }

  async buscarFaqsPorPregunta(pregunta: string): Promise<FaqResponseDto[]> {
    const { data } = await api.get<FaqResponseDto[]>("/publicos/faqs/buscar", {
      params: { pregunta },
    });
    return data;
  }

  // -------------------- Documentos Públicos --------------------

  async obtenerTerminosCondiciones(): Promise<DocumentoResponseDto> {
    const { data } = await api.get<DocumentoResponseDto>(
      "/publicos/documentos/terminos-condiciones"
    );
    return data;
  }

  async obtenerPoliticaPrivacidad(): Promise<DocumentoResponseDto> {
    const { data } = await api.get<DocumentoResponseDto>(
      "/publicos/documentos/politica-privacidad"
    );
    return data;
  }
}
