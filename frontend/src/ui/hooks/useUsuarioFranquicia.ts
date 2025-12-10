import { useState } from "react";

import type { AxiosError } from "axios";
import type {
  FranquiciaResponseDTO,
  FranquiciaRequestDTO,
} from "../../infrastructure/api/dto/Franquicia.dto";
import type { UseFranquiciaResult } from "./types.hook/useFranquicia.type";
import { franquiciaService } from "../../infrastructure/services/usuariofranquiciaServices";

export const useFranquicia = (): UseFranquiciaResult => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [data, setData] = useState<
    string | FranquiciaResponseDTO | FranquiciaResponseDTO[] | boolean | null
  >(null);

  const handleRequest = async <T>(callback: () => Promise<T>): Promise<T> => {
    setIsLoading(true);
    setError(null);
    setData(null);

    try {
      const response = await callback();
      setData(response as unknown as typeof data);
      return response;
    } catch (err: unknown) {
      let message = "Ocurrió un error desconocido.";

      const axiosError = err as AxiosError<{
        message?: string;
        error?: string;
      }>;

      if (axiosError.response) {
        if (typeof axiosError.response.data === "string") {
          message = axiosError.response.data;
        } else if (axiosError.response.data?.message) {
          message = axiosError.response.data.message;
        } else if (axiosError.message) {
          message = axiosError.message;
        }
      } else if (err instanceof Error) {
        message = err.message;
      }

      setError(message);
      throw new Error(message);
    } finally {
      setIsLoading(false);
    }
  };

  // ------------------------------------------
  //  Métodos del servicio
  // ------------------------------------------
  const registrarFranquicia = (payload: FranquiciaRequestDTO) =>
    handleRequest(() => franquiciaService.registrarFranquicia(payload));

  const actualizar = (id: number, dto: FranquiciaRequestDTO) =>
    handleRequest(() => franquiciaService.actualizar(id, dto));

  const obtenerPorId = (id: number) =>
    handleRequest(() => franquiciaService.obtenerPorId(id));

  const obtenerPorSlug = (slug: string) =>
    handleRequest(() => franquiciaService.obtenerPorSlug(slug));

  const listarPorUsuario = (idUsuario: number) =>
    handleRequest(() => franquiciaService.listarPorUsuario(idUsuario));

  const eliminar = (id: number) =>
    handleRequest(() => franquiciaService.eliminar(id));

  const validarNombre = (nombre: string) =>
    handleRequest(() => franquiciaService.validarNombre(nombre));

  const validarSlug = (slug: string) =>
    handleRequest(() => franquiciaService.validarSlug(slug));

  const cambiarEstado = (id: number, estado: string) =>
    handleRequest(() => franquiciaService.cambiarEstado(id, estado));

  return {
    registrarFranquicia,
    actualizar,
    obtenerPorId,
    obtenerPorSlug,
    listarPorUsuario,
    eliminar,
    validarNombre,
    validarSlug,
    cambiarEstado,
    isLoading,
    error,
    data,
  };
};
