import 'package:angular/angular.dart';
import 'package:angular_router/angular_router.dart';
import 'package:sheets_dashboard/welcome-page/welcome_page_component.template.dart';
import 'package:sheets_dashboard/user/user-page/user_page_component.template.dart';
import 'package:sheets_dashboard/resource-page/resource_page_component.template.dart';

@Injectable()
class AppRoutes {
  final List<RouteDefinition> all = [
    new RouteDefinition(
      path: 'welcome-page',
      component: WelcomePageComponentNgFactory,
      useAsDefault: true
    ),
    new RouteDefinition(
      path: 'resources',
      component: ResourcePageComponentNgFactory,
    ),
    new RouteDefinition(
      path: 'user',
      component: UserPageComponentNgFactory,
    ),
  ];
}
