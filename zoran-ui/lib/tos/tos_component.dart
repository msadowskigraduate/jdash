import 'package:angular/angular.dart';
import 'package:angular_components/auto_dismiss/auto_dismiss.dart';
import 'package:angular_components/focus/focus.dart';
import 'package:angular_components/laminate/components/modal/modal.dart';
import 'package:angular_components/laminate/overlay/module.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/material_dialog/material_dialog.dart';
import 'package:angular_components/material_icon/material_icon.dart';
import 'package:angular_components/material_tooltip/material_tooltip.dart';
import 'package:zoran.io/services/user_service.dart';

@Component(
  selector: 'tos-modal-dialog',
  providers: [overlayBindings],
  directives: [
    AutoDismissDirective,
    AutoFocusDirective,
    MaterialIconComponent,
    MaterialButtonComponent,
    MaterialTooltipDirective,
    MaterialDialogComponent,
    ModalComponent,
    NgFor,
    NgIf
  ],
  templateUrl: 'tos_component.html',
  styleUrls: ['tos_component.scss.css'],
)
class TermsOfServiceComponent {
  @Input()
  bool enabled = false;

  final UserService _userService;

  TermsOfServiceComponent(this._userService);

  void activateUser() {
    _userService.activateUser();
    enabled = false;
  }
}