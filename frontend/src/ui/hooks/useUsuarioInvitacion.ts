import { useState, useCallback } from "react";

import type { AxiosError } from "axios";
import type {
  InvitationResponseDTO,
  InvitationRequestDTO,
} from "../../infrastructure/api/types/invitaciones";
import { usuarioinvitacionServices } from "../../infrastructure/services/usuarioinvitacionServices";

const service = new usuarioinvitacionServices();

function isAxiosError(
  error: unknown
): error is AxiosError<{ message: string }> {
  return (
    typeof error === "object" &&
    error !== null &&
    "isAxiosError" in error &&
    (error as AxiosError).isAxiosError
  );
}

export function useUsuarioInvitacion() {
  const [data, setData] = useState<InvitationResponseDTO | null>(null);
  const [list, setList] = useState<InvitationResponseDTO[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleError = (err: unknown) => {
    if (isAxiosError(err)) {
      const message = usuarioinvitacionServices.getErrorMessage(err);
      setError(message);
    } else {
      setError("Error desconocido en la operación.");
    }
  };

  const crearInvitacion = useCallback(async (payload: InvitationRequestDTO) => {
    setLoading(true);
    setError(null);
    try {
      const response = await service.crearInvitacion(payload);
      setData(response);
      return response;
    } catch (err) {
      handleError(err);
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  const obtenerPorToken = useCallback(async (token: string) => {
    setLoading(true);
    setError(null);
    try {
      const response = await service.obtenerPorToken(token);
      setData(response);
      return response;
    } catch (err) {
      handleError(err);
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  const aceptarInvitacion = useCallback(async (token: string) => {
    setLoading(true);
    setError(null);
    try {
      const response = await service.aceptarInvitacion(token);
      setData(response);
      return response;
    } catch (err) {
      handleError(err);
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  const listarPorFranquicia = useCallback(async (idFranquicia: number) => {
    setLoading(true);
    setError(null);
    try {
      const response = await service.listarPorFranquicia(idFranquicia);
      setList(response);
      return response;
    } catch (err) {
      handleError(err);
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  return {
    data,
    list,
    loading,
    error,
    crearInvitacion,
    obtenerPorToken,
    aceptarInvitacion,
    listarPorFranquicia,
  };
}
