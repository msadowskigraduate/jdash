import 'dart:async';

class NotificationService {
  final _onError = new StreamController<String>.broadcast();

  void sendError(String message) {
    _onError.add(message);
  }

  Stream<String> get error => _onError.stream;
}
