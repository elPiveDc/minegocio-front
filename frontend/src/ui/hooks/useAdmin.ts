import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";

import type {
  FaqRequestDto,
  DocumentoRequestDto,
  FaqResponseDto,
  DocumentoMetadataResponseDto,
  ConsultaResponseDto,
  ConsultaUpdateDto,
  ConsultaEstadoDto,
  ConsultaRespuestaDto,
} from "../../infrastructure/api/dto/FaqDocumentoConsulta.dto";

import { adminService } from "../../infrastructure/services/adminServices";

import type { Faq } from "../../domain/models/Faq";
import type { Franquicia } from "../../domain/models/Franquicia";
import type { Consulta } from "../../domain/models/Consulta";

export const queryKeys = {
  faqs: ["faqs"],
  documentos: ["documentos"],
  franquicias: ["franquicias"],
  consultas: ["consultas"],
  consultasEstado: (estado: string) => ["consultas", estado],
};

export const useAdminService = () => {
  const queryClient = useQueryClient();

  const useObtenerFaqs = () =>
    useQuery<Faq[], Error>({
      queryKey: queryKeys.faqs,
      queryFn: adminService.obtenerFaqs,
    });

  const useCrearFaq = () =>
    useMutation<FaqResponseDto, Error, FaqRequestDto>({
      mutationFn: adminService.crearFaq,
      onSuccess: () =>
        queryClient.invalidateQueries({ queryKey: queryKeys.faqs }),
    });

  const useActualizarFaq = () =>
    useMutation<FaqResponseDto, Error, { id: number; payload: FaqRequestDto }>({
      mutationFn: ({ id, payload }) => adminService.actualizarFaq(id, payload),
      onSuccess: () =>
        queryClient.invalidateQueries({ queryKey: queryKeys.faqs }),
    });

  const useEliminarFaq = () =>
    useMutation<void, Error, number>({
      mutationFn: adminService.eliminarFaq,
      onSuccess: () =>
        queryClient.invalidateQueries({ queryKey: queryKeys.faqs }),
    });

  const useObtenerDocumentos = () =>
    useQuery<DocumentoMetadataResponseDto[], Error>({
      queryKey: queryKeys.documentos,
      queryFn: adminService.obtenerDocumentos,
    });

  const useCrearDocumento = () =>
    useMutation<unknown, Error, DocumentoRequestDto>({
      mutationFn: adminService.crearDocumento,
      onSuccess: () =>
        queryClient.invalidateQueries({ queryKey: queryKeys.documentos }),
    });

  const useActualizarDocumento = () =>
    useMutation<unknown, Error, { id: number; payload: DocumentoRequestDto }>({
      mutationFn: ({ id, payload }) =>
        adminService.actualizarDocumento(id, payload),
      onSuccess: () =>
        queryClient.invalidateQueries({ queryKey: queryKeys.documentos }),
    });

  const useEliminarDocumento = () =>
    useMutation<void, Error, number>({
      mutationFn: adminService.eliminarDocumento,
      onSuccess: () =>
        queryClient.invalidateQueries({ queryKey: queryKeys.documentos }),
    });

  const useListarFranquicias = () =>
    useQuery<Franquicia[], Error>({
      queryKey: queryKeys.franquicias,
      queryFn: adminService.listarFranquicias,
    });

  const useObtenerTodasConsultas = () =>
    useQuery<Consulta[], Error>({
      queryKey: queryKeys.consultas,
      queryFn: adminService.obtenerTodasConsultas,
    });

  const useObtenerConsultasPorEstado = (estado: string) =>
    useQuery<ConsultaResponseDto[], Error>({
      queryKey: queryKeys.consultasEstado(estado),
      queryFn: () => adminService.obtenerConsultasPorEstado(estado),
    });

  const useActualizarConsulta = () =>
    useMutation<
      ConsultaResponseDto,
      Error,
      { id: number; payload: ConsultaUpdateDto }
    >({
      mutationFn: ({ id, payload }) =>
        adminService.actualizarConsulta(id, payload),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: queryKeys.consultas });
      },
    });

  const useCambiarEstadoConsulta = () =>
    useMutation<
      ConsultaResponseDto,
      Error,
      { id: number; payload: ConsultaEstadoDto }
    >({
      mutationFn: ({ id, payload }) =>
        adminService.cambiarEstadoConsulta(id, payload),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: queryKeys.consultas });
      },
    });

  const useResponderConsulta = () =>
    useMutation<
      ConsultaResponseDto,
      Error,
      { id: number; payload: ConsultaRespuestaDto }
    >({
      mutationFn: ({ id, payload }) =>
        adminService.responderConsulta(id, payload),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: queryKeys.consultas });
      },
    });

  const useEliminarConsulta = () =>
    useMutation<void, Error, number>({
      mutationFn: adminService.eliminarConsulta,
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: queryKeys.consultas });
      },
    });

  return {
    useObtenerFaqs,
    useCrearFaq,
    useActualizarFaq,
    useEliminarFaq,

    useObtenerDocumentos,
    useCrearDocumento,
    useActualizarDocumento,
    useEliminarDocumento,

    useListarFranquicias,

    useObtenerTodasConsultas,
    useObtenerConsultasPorEstado,
    useActualizarConsulta,
    useCambiarEstadoConsulta,
    useResponderConsulta,
    useEliminarConsulta,
  };
};
