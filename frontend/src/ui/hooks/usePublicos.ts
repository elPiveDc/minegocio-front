import { useState, useCallback } from "react";
import type { AxiosError } from "axios";
import type { BackendErrorData } from "../../infrastructure/api/types/errors";
import { PublicosService } from "../../infrastructure/services/publicosService";
import type {
  DocumentoResponseDto,
  FaqResponseDto,
} from "../../infrastructure/api/dto/FaqDocumentoConsulta.dto";

const service = new PublicosService();

export function usePublicos() {
  const [faqs, setFaqs] = useState<FaqResponseDto[]>([]);
  const [documento, setDocumento] = useState<DocumentoResponseDto | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleError = (err: unknown, fallback: string) => {
    if (isAxiosErrorWithData(err)) {
      setError(err.response?.data?.message ?? fallback);
    } else {
      setError(fallback);
    }
  };

  function isAxiosErrorWithData(
    error: unknown
  ): error is AxiosError<BackendErrorData> {
    return (
      typeof error === "object" &&
      error !== null &&
      "isAxiosError" in error &&
      (error as AxiosError).isAxiosError
    );
  }

  const obtenerTodasFaqs = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await service.obtenerTodasFaqs();
      setFaqs(data);
      return data;
    } catch (err: unknown) {
      handleError(err, "Error al cargar FAQs");
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  const buscarFaqsPorPregunta = useCallback(async (pregunta: string) => {
    setLoading(true);
    setError(null);
    try {
      const data = await service.buscarFaqsPorPregunta(pregunta);
      setFaqs(data);
      return data;
    } catch (err: unknown) {
      handleError(err, "Error al buscar FAQs");
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  const obtenerTerminosCondiciones = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await service.obtenerTerminosCondiciones();
      setDocumento(data);
      return data;
    } catch (err: unknown) {
      handleError(err, "Error al cargar Términos y Condiciones");
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  const obtenerPoliticaPrivacidad = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await service.obtenerPoliticaPrivacidad();
      setDocumento(data);
      return data;
    } catch (err: unknown) {
      handleError(err, "Error al cargar Política de Privacidad");
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  return {
    faqs,
    documento,
    loading,
    error,
    obtenerTodasFaqs,
    buscarFaqsPorPregunta,
    obtenerTerminosCondiciones,
    obtenerPoliticaPrivacidad,
  };
}
