import { Miembro } from "../../miembros/miembro";

export class Subtarea {
  id: number;
  descripcion: string;
  observacion: string;
  miembro: Miembro;
  duracion: number;
  tiempo_estimado: number;
  estado: string;
}
