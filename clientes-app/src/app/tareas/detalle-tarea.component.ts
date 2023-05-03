import { Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { Tarea } from './models/tarea';
import { TareaService } from './services/tarea.service';
import { Subtarea } from './models/subtarea';
import { RelacionTarea } from './models/relacion_tarea';
import { MatAutocompleteSelectedEvent } from '@angular/material';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, flatMap } from 'rxjs/operators';

@Component({
  selector: 'app-detalle-tarea',
  templateUrl: './detalle-tarea.component.html'
})
export class DetalleTareaComponent implements OnInit {

  tarea: Tarea;
  titulo: string = 'Tarea';
  subtareas:Subtarea[];
  autocompleteControl = new FormControl();

  productosFiltrados: Observable<Subtarea[]>;

  constructor(private tareasService: TareaService,
  private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.activatedRoute.paramMap.subscribe(params => {
      let id = +params.get('id');
      this.tareasService.getTarea(id).subscribe(tarea => {
        this.tarea = tarea;
      })
    });
    this.tareasService.filtrarSubtareas().subscribe(subtareas => this.subtareas = subtareas);
    this.productosFiltrados = this.autocompleteControl.valueChanges
      .pipe(
        // startWith(''),
        map(value => typeof value === 'string' ? value : value.descripcion),
        flatMap(value => value ? this._filter(value) : [])
      );
  }

  private _filter(value: string): Observable<Subtarea[]> {
    const filterValue = value.toLowerCase();

    return this.tareasService.filtrarProductos(filterValue);
  }

  mostrarNombre(subtarea?: Subtarea): string | undefined {
    return subtarea ? subtarea.descripcion : undefined;
  }

  seleccionarSubtarea(event: MatAutocompleteSelectedEvent): void {
    let subtarea = event.option.value as Subtarea;
    console.log(subtarea);


    let nuevoItem = new RelacionTarea();
    nuevoItem.subtarea = subtarea;
    this.tarea.relaciones.push(nuevoItem);

    this.autocompleteControl.setValue('');
    event.option.focus();
    event.option.deselect();

  }

  eliminarItemFactura(id: number): void {
    this.tarea.relaciones = this.tarea.relaciones.filter((item: RelacionTarea) => id !== item.subtarea.id);
  }

  guardar(tarea: Tarea): void {
    console.log(this.tarea);
    swal({
      title: 'Está seguro?',
      text: `¿Seguro que desea modificar la tarea ${tarea.descripcion}?`,
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, actualizar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
      buttonsStyling: false,
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        this.tarea = tarea;
        this.tareasService.create(tarea).subscribe(
          () => {
            swal(
              'Tarea actualizada!',
              `Tarea ${tarea.descripcion} actualizada con éxito.`,
              'success'
            )
          //  this.router.navigate(['/miembros'])
          }
        )

      }
    });
  }
}
