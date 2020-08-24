import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TableMitarbeiterComponent} from "./components/mitarbeiter/table-mitarbeiter.component";

const routes: Routes = [
  {path: 'mitarbeiter', component: TableMitarbeiterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
