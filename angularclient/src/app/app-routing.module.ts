import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MitarbeiterComponent} from "./components/mitarbeiter/mitarbeiter.component";

const routes: Routes = [
  {path: 'mitarbeiter', component: MitarbeiterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
