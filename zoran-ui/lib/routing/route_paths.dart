import 'package:angular_router/angular_router.dart';

const uriParam = 'uri';

class RoutePaths {
  static final resource = RoutePath(path: 'resource/:$uriParam');
  static final pipeline = RoutePath(path: 'pipeline/:$uriParam');
}

String getUrl(Map<String, String> parameters) {
  final id = parameters[uriParam];
  return id;
}