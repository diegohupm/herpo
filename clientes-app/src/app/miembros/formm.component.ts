import { Component, OnInit } from '@angular/core';
import { Miembro } from './miembro';
import { MiembroService } from './miembro.service';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './formm.component.html',
  styleUrls: ['./formm.component.css'],
})
export class FormmComponent implements OnInit {

  public miembro: Miembro = new Miembro();
  titulo:string = "Crear Miembro";

  errores: string[];

  constructor(private miembroService: MiembroService,
  private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.cargarMiembro();
  }

  cargarMiembro(): void {
    this.activatedRoute.params.subscribe(params => {
      let id= params['id'];
      if(id){
        this.miembroService.getMiembro(id).subscribe( (miembro) => this.miembro = miembro)
      }
    });
  }

  create(): void {
    this.miembroService.create(this.miembro).subscribe(
      miembro => {
        this.router.navigate(['/miembros']);
        swal('Nuevo miembro', `El miembro ${miembro.nombre} ha sido creado con éxito.`, 'success');
      },
      err => {
        this.errores = err.error.errors as string[];
        console.error('Código del error desde el backend: ' + err.status);
        console.error(err.error.errors);
      }
    );
  }

  update(): void{
    // this.miembro.facturas = null;
    this.miembro.tareas = null;
    this.miembroService.update(this.miembro).subscribe(
      miembro => {
        this.router.navigate(['/miembros'])
        swal('Miembro actualizado', `El miembro ${miembro.nombre} ha sido editado con éxito.`, 'success')
      },
      err => {
        this.errores = err.error.errors as string[];
        console.error('Código del error desde el backend: ' + err.status);
        console.error(err.error.errors);
      }
    );
  }

}
