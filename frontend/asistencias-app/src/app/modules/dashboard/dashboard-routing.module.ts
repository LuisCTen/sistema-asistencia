import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { HistoryPageComponent } from './pages/history-page/history-page.component';
import { JustificationPageComponent } from './pages/justification-page/justification-page.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardPageComponent,
    children: [
      //Aqui irá history, reports (como hijos)
    ],
  },
  {
    path: 'history',
    component: HistoryPageComponent,
  },
  {
    path: 'justification',
    component: JustificationPageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule {}
