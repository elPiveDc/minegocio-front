export interface BackendErrorData {
  message: string;
}

export interface NetworkErrorWithData {
  response: {
    data: BackendErrorData;
  };
}
