import type { AxiosError } from "axios";
import type {
  InvitationRequestDTO,
  InvitationResponseDTO,
} from "../api/types/invitaciones";
import { api } from "../api/api";

export class usuarioinvitacionServices {
  async crearInvitacion(
    payload: InvitationRequestDTO
  ): Promise<InvitationResponseDTO> {
    const { data } = await api.post<InvitationResponseDTO>(
      "/usuarios/invitacion/crear",
      payload
    );
    return data;
  }

  async obtenerPorToken(token: string): Promise<InvitationResponseDTO> {
    const { data } = await api.get<InvitationResponseDTO>(
      `/usuarios/invitacion/${token}`
    );
    return data;
  }

  async aceptarInvitacion(token: string): Promise<InvitationResponseDTO> {
    const { data } = await api.post<InvitationResponseDTO>(
      `/usuarios/invitacion/${token}/accept`
    );
    return data;
  }

  async listarPorFranquicia(
    idFranquicia: number
  ): Promise<InvitationResponseDTO[]> {
    const { data } = await api.get<InvitationResponseDTO[]>(
      `/usuarios/invitacion/franquicias/${idFranquicia}`
    );
    return data;
  }

  static getErrorMessage(error: AxiosError<{ message: string }>): string {
    return (
      error.response?.data?.message ?? "Error desconocido en la operación."
    );
  }
}
