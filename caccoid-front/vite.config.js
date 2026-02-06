import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host: true,        // permite acesso externo ao servidor Vite (necessário no Docker)
    port: 5173,        // porta padrão que você já expôs no docker-compose
    strictPort: true   // evita fallback em outra porta caso a 5173 esteja ocupada
  }
});