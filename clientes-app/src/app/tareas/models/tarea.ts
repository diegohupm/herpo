import { RelacionTarea } from './relacion_tarea';
import { Miembro } from "../../miembros/miembro";

export class Tarea {
  id: number;
  descripcion: string;
  observacion: string;
  miembro: Miembro;
  duracion: number;
  tiempo_estimado: number;
  estado: string;
  relaciones: Array<RelacionTarea> = [];
}
