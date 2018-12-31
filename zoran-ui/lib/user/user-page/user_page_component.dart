import 'dart:html';

import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_router/angular_router.dart';
import 'package:sheets_dashboard/401/401_page_component.dart';
import 'package:sheets_dashboard/user/user_service.dart';
import 'package:angular/security.dart';

@Component(
  selector: 'user',
  templateUrl: 'user_page_component.html',
  directives: const [
    formDirectives,
    coreDirectives,
    routerDirectives,
    MaterialIconComponent,
    MaterialButtonComponent,
    UnauthorizedComponent,
    MaterialInputComponent
  ],
  styleUrls: const ['user_page_component.scss.css'],
  providers: const [
    materialProviders
  ],
)
class UserPageComponent implements OnInit {
  final Router router;
  final UserService userService;
  final DomSanitizationService sanitizer;
  SafeUrl sanitizedAvatarUrl;
  UserDto user;
  Element img;
  UserPageComponent(this.router, this.userService, this.sanitizer);

  bool editable;

  @override
  void ngOnInit() {
    this.user = userService.user;
    if(user == null) {
      navigate("login");
    } else {
      updateAndSanitizeSheetUrl();
    }
  }

  void navigate(String uri) {
    router.navigate(uri);
  }

  void updateAndSanitizeSheetUrl() {
    sanitizedAvatarUrl = sanitizer.bypassSecurityTrustUrl(user.avatarUrl);
  }

  void sanitizeandnavigate() {
     window.location.assign(user.htmlUrl);
  }

  void deactivate() async {
    userService.deactivateUser();
    user = userService.user;
  }
}