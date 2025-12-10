export interface InvitationResponseDTO {
  id: number;
  idFranquicia: number;
  invitedEmail: string;
  invitedBy: number;
  token: string;
  roleOffered: string;
  status: string;
  expiresAt: string | null;
  createdAt: string | null;
  acceptedAt: string | null;
}

export interface InvitationRequestDTO {
  idFranquicia: number;
  invitedEmail: string;
  invitedBy: number;
  roleOffered: string;
  expiresAt: string | null;
}

// Backend Error Types
export interface BackendErrorData {
  message: string;
}

export interface NetworkErrorWithData {
  response: {
    data: BackendErrorData;
  };
}
