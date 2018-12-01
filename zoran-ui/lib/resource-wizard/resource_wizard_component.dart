import 'package:angular/angular.dart';
import 'package:angular_components/material_dialog/material_dialog.dart';
import 'package:angular_components/material_list/material_list.dart';

@Component(
  selector: 'resource-wizard',
  styleUrls: const ['resource_wizard_component.scss.css'],
  templateUrl: 'resource_wizard_component.html',
  directives: const [
    coreDirectives,
    MaterialPersistentDrawerDirective,
    MaterialButtonComponent,
    MaterialIconComponent,
    MaterialListComponent,
    MaterialListItemComponent,
    MaterialDialogComponent,
    DeferredContentDirective,
    AutoDismissDirective,
    ModalComponent,
    AutoFocusDirective,
    GlobalEscapeDirective,
    routerDirectives,
    UserComponent,
    FooterComponent,
    UnauthorizedComponent
  ],
  providers: const [materialProviders,
  const ClassProvider(AppRoutes),
  const ClassProvider(UserService)
  ],
)
class ResourceWizardComponent {

}