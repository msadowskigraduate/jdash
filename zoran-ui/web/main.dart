import 'dart:html';

import 'package:angular/angular.dart';
import 'package:angular_router/angular_router.dart';
import 'package:http/browser_client.dart';
import 'package:http/http.dart' as http;
import 'package:logging/logging.dart';
import 'package:zoran.io/notification_service.dart';
import 'package:zoran.io/services/resource_service.dart';
import 'package:zoran.io/services/zoran_service.dart';

import 'main.template.dart' as ng;
import 'package:zoran.io/app_component.template.dart' as app;

@GenerateInjector(const [
  const ClassProvider(NotificationService),
  const ClassProvider(ZoranService),
  const ClassProvider(NewResourceService),
  const ClassProvider(ExceptionHandler, useClass: MyExceptionHandler),
  const ClassProvider(http.Client, useClass: BrowserClient),
//  const ValueProvider.forToken(zoranIoUrl, ""),
  const ValueProvider.forToken(zoranIoUrl, "http://localhost:8080"),
  routerProvidersHash,
])
final InjectorFactory dashboardApp = ng.dashboardApp$Injector;
/// Represents the base URL for HTTP requests using [ZoranService].
const zoranIoUrl = const OpaqueToken<String>('zoranBaseUrl');
void main() {
  Logger.root.level = Level.ALL;
  Logger.root.onRecord.listen((LogRecord rec) {
    print('${rec.level.name}: ${rec.time}: ${rec.message}');
  });
  runApp(app.AppComponentNgFactory, createInjector: dashboardApp);
}

@Injectable()
class MyExceptionHandler implements ExceptionHandler {
  const MyExceptionHandler();

  @override
  void call(error, [stack, String reason]) => handle(error, stack, reason);

  void handle(exception, [stack, String reason]) {
    window.console.error(ExceptionHandler.exceptionToString(
      exception,
      stack,
      reason,
    ));
  }
}
