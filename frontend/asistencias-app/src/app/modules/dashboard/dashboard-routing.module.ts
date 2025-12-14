import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { HistoryPageComponent } from './pages/history-page/history-page.component';
import { JustificationPageComponent } from './pages/justification-page/justification-page.component';
import { AdminInboxPageComponent } from './pages/admin-inbox-page/admin-inbox-page.component';
import { UsersPageComponent } from './pages/users-page/users-page.component';

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
  { path: 'admin/inbox', component: AdminInboxPageComponent },
  { path: 'admin/users', component: UsersPageComponent },
  {
    path: 'admin/inbox',
    component: AdminInboxPageComponent,
  },
  // Redirección por defecto
  { path: '**', redirectTo: 'home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule {}
