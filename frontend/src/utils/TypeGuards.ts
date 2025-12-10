import type { NetworkErrorWithData } from "../infrastructure/api/types/errors";

export const hasBackendErrorMessage = (
  error: unknown
): error is NetworkErrorWithData => {
  if (typeof error !== "object" || error === null) {
    return false;
  }

  if (
    !("response" in error) ||
    typeof error.response !== "object" ||
    error.response === null
  ) {
    return false;
  }

  const response = error.response as Record<string, unknown>;
  if (
    !("data" in response) ||
    typeof response.data !== "object" ||
    response.data === null
  ) {
    return false;
  }

  const data = response.data as Record<string, unknown>;
  return "message" in data && typeof data.message === "string";
};
