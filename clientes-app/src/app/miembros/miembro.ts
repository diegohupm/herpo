import { Tarea } from "../tareas/models/tarea";
import { Subtarea } from "../tareas/models/subtarea";

export class Miembro {
  id: number;
  nombre: string;
  apellido: string;
  createAt: string;
  email: string;
  foto: string;
  tareas: Array<Tarea> = [];
  cargo: string;
  proyecto: string;
  subtareas: Array<Subtarea> = [];
}
