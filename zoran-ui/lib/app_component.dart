import 'package:angular/angular.dart';
import 'package:angular_components/utils/keyboard/global_escape_directive.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_router/angular_router.dart';
import 'package:zoran.io/401/401_page_component.dart';
import 'package:zoran.io/footer/footer_component.dart';
import 'package:zoran.io/management/pipeline_component.dart';
import 'package:zoran.io/management/task-viewer/task_viewer.dart';
import 'package:zoran.io/notification_service.dart';
import 'package:zoran.io/resource-browser/resource_browser_component.dart';
import 'package:zoran.io/routing/routing.dart';
import 'package:zoran.io/user/user_component.dart';
import 'package:zoran.io/services/user_service.dart';

@Component(
  selector: 'my-app',
  styleUrls: const ['app_component.scss.css', 'package:angular_components/app_layout/layout.scss.css'],
  templateUrl: 'app_component.html',
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
    UnauthorizedComponent,
    ResourceBrowserComponent,
    PipelineComponent,
    TaskViewer
  ],
  providers: const [materialProviders,
  const ClassProvider(AppRoutes),
  const ClassProvider(UserService)
  ],
)
class AppComponent implements OnInit {
  final AppRoutes routes;
  final Router router;
  final NotificationService notificationService;
  AppComponent(this.routes, this.notificationService, this.router);

  String notificationMessage;
  bool notificationVisible = false;
  String iconColor = 'blue';
  String bannerIconColor = 'white';
  @override
  void ngOnInit() {
    notificationService.error.listen((message) {
      notificationMessage = message;
      notificationVisible = true;
    });
  }

  void navigate(String url) {
    this.router.navigate(url);
  }
}
