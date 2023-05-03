import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import swal from 'sweetalert2';
import { MiembroService } from '../miembros/miembro.service';
import { Tarea } from './models/tarea';
import { TareaService } from './services/tarea.service';

@Component({
  selector: 'app-tareas',
  templateUrl: './tareas.component.html'
})
export class TareasComponent implements OnInit {

  titulo: string = 'Crear una tarea';
  tarea: Tarea = new Tarea();

  constructor(private miembroService: MiembroService,
    private tareaService: TareaService,
    private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params =>{
      let miembroId = +params.get('miembroId');
      this.miembroService.getMiembro(miembroId).subscribe(miembro => this.tarea.miembro = miembro);
    });

  }

  create(tareaForm:any):void {
    console.log(this.tarea);
  //  if(this.tarea.items.length == 0) {
    //  this.autocompleteControl.setErrors({'invalid':true});
    //}

  //  if(facturaForm.form.valid && this.tarea.items.length > 0) {
    if(tareaForm.form.valid) {
      this.tareaService.create(this.tarea).subscribe(tarea => {
      swal(this.titulo, `Tarea ${tarea.descripcion} creada con éxito`, 'success');
      this.router.navigate(['/miembros'])
    })
    }

  }

}
