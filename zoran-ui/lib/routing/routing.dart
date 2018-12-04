import 'package:angular/angular.dart';
import 'package:angular_router/angular_router.dart';
import 'package:sheets_dashboard/routing/route_paths.dart';
import 'package:sheets_dashboard/welcome-page/welcome_page_component.template.dart';
import 'package:sheets_dashboard/user/user-page/user_page_component.template.dart';
import 'package:sheets_dashboard/resource-page/resource_page_component.template.dart';
import 'package:sheets_dashboard/resource-wizard/resource_wizard_component.template.dart';
import 'package:sheets_dashboard/resource-browser/resource_browser_component.template.dart';
import 'package:sheets_dashboard/resource-browser/resource-view/resource_view_component.template.dart';

@Injectable()
class AppRoutes {
  final List<RouteDefinition> all = [
    new RouteDefinition(
        path: 'welcome-page',
        component: WelcomePageComponentNgFactory,
        useAsDefault: true),
    new RouteDefinition(
      path: 'resources',
      component: ResourcePageComponentNgFactory,
    ),
    new RouteDefinition(
      path: 'user',
      component: UserPageComponentNgFactory,
    ),
    new RouteDefinition(
      path: 'new-resource',
      component: ResourceWizardComponentNgFactory,
    ),
    new RouteDefinition(
      path: 'resource_browser_component',
      component: ResourceBrowserComponentNgFactory,
    ),
    new RouteDefinition(
      path: RoutePaths.resource.path,
      component: ResourceViewComponentNgFactory,
    )
  ];
}
