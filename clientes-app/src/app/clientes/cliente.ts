import { Factura } from '../facturas/models/factura';

export class Cliente {
  id: number;
  empresa: string;
  proyecto: string;
  createAt: string;
  email: string;
  foto: string;
  facturas: Array<Factura> = [];
}
