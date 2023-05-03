import { Injectable } from '@angular/core';
// import { formatDate } from '@angular/common';
// import { DatePipe } from '@angular/common';
// import { CLIENTES } from './miembros.json';
import { Miembro } from './miembro';
// import { of, Observable } from 'rxjs';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import {map, tap, catchError } from 'rxjs/operators';
// import swal from 'sweetalert2';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MiembroService {

  private urlEndPoint:string = 'http://localhost:8090/api/miembros';

  constructor(private http: HttpClient, private router: Router) { }

  getMiembros(page: number): Observable<any> {
    return this.http.get(this.urlEndPoint + '/page/' + page).pipe(
      tap((response: any) => {
        console.log('MiembroService: tap 1');
        (response.content as Miembro[]).forEach(miembro => console.log(miembro.nombre));
      }),
      map((response: any) => {
        (response.content as Miembro[]).map(miembro => {
          // miembro.nombre = miembro.nombre.toUpperCase();
          // let datePipe = new DatePipe('es');
          // miembro.createAt = datePipe.transform(miembro.createAt, 'EEEE dd, MMMM yyyy');
          // miembro.createAt = formatDate(miembro.createAt, 'dd-MM-yyyy', 'en-US');
          return miembro;
        });
        return response;
      }),
      tap(response => {
        console.log('MiembroService: tap 2');
        (response.content as Miembro[]).forEach(miembro => console.log(miembro.nombre));
      })
    );
  }


  create(miembro: Miembro) : Observable<Miembro> {
    return this.http.post(this.urlEndPoint, miembro).pipe(
      map((response:any) => response.miembro as Miembro),
      catchError(e => {
        if(e.status == 400){
          return throwError(e);
        }
        console.error(e.error.mensaje);
        // swal(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getMiembro(id:number): Observable<Miembro> {
    return this.http.get<Miembro>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e => {
        if (e.status != 401 && e.error.mensaje) {
          this.router.navigate(['/miembros']);
          console.error(e.error.mensaje);
        }
        return throwError(e);
      })
    );
  }


  update(miembro: Miembro): Observable<Miembro>{
    return this.http.put<any>(`${this.urlEndPoint}/${miembro.id}`, miembro).pipe(
      map((response:any) => response.miembro as Miembro),
      catchError(e => {
        if(e.status == 400){
          return throwError(e);
        }
        console.error(e.error.mensaje);
        // swal(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  delete(id: number): Observable<Miembro>{
    return this.http.delete<Miembro>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e => {
        console.error(e.error.mensaje);
        // swal(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  subirFoto(archivo: File, id:any): Observable<HttpEvent<{}>>{
    let formData = new FormData();
    formData.append("archivo", archivo);
    formData.append("id", id);

    const req = new HttpRequest('POST', `${this.urlEndPoint}/upload`, formData, {
      reportProgress: true
    });
    return this.http.request(req);
  }
}
