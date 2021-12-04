this is code from https://github.com/anonfunc/intellij-voicecode
moved to plugin env that supported by the IntellijIDEA 2021.3

it's not tested at all, use it at your own risk

to use it from talon you'd have to change port number in jetbrains.py
to value that you'd be able to find in the `Settings/Build,Deploy.Execute/Debugger/Builtin Server/Port
`

also, you'd need to change this function:

`def _get_nonce(port, file_prefix):`<br>
`   return 'vcidea'`
