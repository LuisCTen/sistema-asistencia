import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { HistoryPageComponent } from './pages/history-page/history-page.component';
import { JustificationPageComponent } from './pages/justification-page/justification-page.component';
import { AdminInboxPageComponent } from './pages/admin-inbox-page/admin-inbox-page.component';
import { UsersPageComponent } from './pages/users-page/users-page.component';
import { UserFormComponent } from './components/user-form/user-form.component';


@NgModule({
  declarations: [
    DashboardPageComponent,
    HistoryPageComponent,
    JustificationPageComponent,
    AdminInboxPageComponent,
    UsersPageComponent,
    UserFormComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    SharedModule
  ]
})
export class DashboardModule { }
