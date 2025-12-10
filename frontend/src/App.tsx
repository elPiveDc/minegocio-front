import React from "react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import AppRouter from "./ui/router/AppRouter";

const App: React.FC = () => {
  const queryClient = new QueryClient();

  return (
    <div className="app-container">
      <QueryClientProvider client={queryClient}>
        <AppRouter />
      </QueryClientProvider>
    </div>
  );
};

export default App;
