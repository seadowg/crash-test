# crash-test

Usually, when an uncaught `Exception` occurs in an Android app process, the user is shown a dialog by the system informing them that the app has "stopped" or "crashed". If the user hits "Close app" the process is killed.

Sometimes though, no dialog is shown and the app simply "restarts" when it crashes: the process is killed and then started (creating a new `Application` object and running its `onCreate`), and the back stack is recreated. This seems to only happen if the app hasn't crashed recently. What's surprising here is that the back stack is recreated with the exception of the Activity that was on top when the crash occurred. This also means that, as far as I've seen, we never end up doing the restart if the crash occurs with only one Activity in the stack which will be the standard state for apps that use a single Activity/multi Fragment architecture (or Compose).

I created this project to demonstrate/confirm the above behaviour. There are [several](https://stackoverflow.com/questions/5651651/prevent-android-from-recreating-activity-stack-after-crash) [Stack Overlflow posts](https://stackoverflow.com/questions/5423571/prevent-activity-stack-from-being-restored) [that mention this](https://stackoverflow.com/questions/5651651/prevent-android-from-recreating-activity-stack-after-crash) restart behaviour, but I haven't managed to find any documentation detailing it (please send it to me if you find it). I'd have assumed that this behaviour would be identical to the the restart that occurs when Android kills your app to reclaim memory and then the user navigates back to it. In that case however, the back stack is kept as it was before on relaunch (as you'd expect).

You never want to have your users experiences crashes in an app you create, but you'd be naive to believe that will never happen so accounting for this behaviour is probably wise. For instance, you should probably avoid having any initialization or navigation code in a "launch" Activity that's doesn't stick around in the back stack (either through `noHistory` or a `finish()` call) - this would could result in problems when the back stack is recreated wtihout it. That's also something to consider for the the standard app restart case but it's also worth considering where users might end up in the specific case of a crash restart: hopefully they end up on a "main menu" screen, but this behaviour could land them somewhere strange due to the "head" of the back stack being chopped off and if you have a single Activity you'll not get the blessing (or curse) of the restart.