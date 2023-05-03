import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tarea } from '../models/tarea';
import { Subtarea } from '../models/subtarea';
// import { URL_BACKEND } from '../../config/config';

@Injectable({
  providedIn: 'root'
})
export class TareaService {
  // private urlEndPoint: string = URL_BACKEND + '/api/tareas';
  private urlEndPoint: string = 'http://localhost:8090/api/tareas';
  private urlEndPointSubtarea: string = 'http://localhost:8090/api/tareas/subtareas';

  constructor(private http: HttpClient) { }

  getTarea(id: number): Observable<Tarea>{
    return this.http.get<Tarea>(`${this.urlEndPoint}/${id}`);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.urlEndPoint}/${id}`);
  }

  create(tarea: Tarea): Observable<Tarea> {
    return this.http.post<Tarea>(this.urlEndPoint, tarea);
  }

  actualizar(tarea: Tarea): Observable<Tarea> {
    return this.http.put<Tarea>(this.urlEndPoint, tarea);
  }

  filtrarSubtareas(): Observable<Subtarea[]> {
    return this.http.get<Subtarea[]>(`${this.urlEndPointSubtarea}`);
  }

  filtrarProductos(term: string): Observable<Subtarea[]> {
    return this.http.get<Subtarea[]>(`${this.urlEndPoint}/filtrar-subtareas/${term}`);
  }

  getSubtarea(id_subtarea: number): Observable<Subtarea>{
    return this.http.get<Subtarea>(`${this.urlEndPointSubtarea}/${id_subtarea}`);
  }

  deleteSubtarea(id_subtarea: number): Observable<void> {
    return this.http.delete<void>(`${this.urlEndPointSubtarea}/${id_subtarea}`);
  }

  createSubtarea(subtarea: Subtarea): Observable<Subtarea> {
    return this.http.post<Subtarea>(this.urlEndPointSubtarea, subtarea);
  }

  actualizarSubtarea(subtarea: Subtarea): Observable<Subtarea> {
    return this.http.put<Subtarea>(this.urlEndPointSubtarea, subtarea);
  }

}
