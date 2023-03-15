import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.fourlastor.plugin.WildsExtensionPoint
import io.github.fourlastor.plugin.model.Species
import org.pf4j.DefaultPluginManager
import org.pf4j.JarPluginLoader
import org.pf4j.PluginClassLoader
import org.pf4j.PluginDescriptor
import org.pf4j.PluginLoader
import org.pf4j.PluginManager
import java.nio.file.Path
import kotlin.io.path.Path

@Composable
@Preview
fun App(species: List<Species>) {
    MaterialTheme {
        Row {
            Surface(
                elevation = 2.dp,
                modifier = Modifier.width(300.dp).fillMaxHeight()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(species.size) {
                        Card(
                            modifier = Modifier.size(140.dp).clickable { println("clicked!") },
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                            Box {
                                val monster = species[it]
                                Image(
                                    modifier = Modifier.size(80.dp).align(Alignment.Center),
                                    bitmap = loadBitmap("${monster.name.lowercase()}/front.png"),
                                    contentDescription = null,
                                    alignment = Alignment.TopCenter,
                                    contentScale = ContentScale.Crop,
                                    filterQuality = FilterQuality.None,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun loadBitmap(path: String) =
    remember(path) { useResource(path, ::loadImageBitmap) }

fun main() {
    val manager = SecurePluginManager(Path("/home/daniele/Documents/wilds/mod-explorer/plugin/build/libs"))
    manager.loadPlugins()
    manager.startPlugins()
    val species = manager.getExtensions(WildsExtensionPoint::class.java)
        .flatMap { it.species() }
    application {
        Window(onCloseRequest = ::exitApplication) {
            App(species)
        }
    }
}

class SecurePluginManager(path: Path) : DefaultPluginManager(path) {

    override fun createPluginLoader(): PluginLoader {
        return SecurePluginLoader(this)
    }
}

class SecurePluginLoader(pluginManager: PluginManager) : JarPluginLoader(pluginManager) {

    override fun loadPlugin(pluginPath: Path, pluginDescriptor: PluginDescriptor): ClassLoader {
        val pluginClassLoader = PluginClassLoader(
            pluginManager,
            pluginDescriptor,
            javaClass.classLoader,
        )
        pluginClassLoader.addFile(pluginPath.toFile())

        return pluginClassLoader
    }
}

class SecureClassLoader(pluginManager: PluginManager, pluginDescriptor: PluginDescriptor, parent: ClassLoader) : PluginClassLoader(pluginManager, pluginDescriptor, parent) {
    override fun loadClass(className: String): Class<*> {
        if (className.startsWith("java.io")) {
            throw ClassNotFoundException("$className is not allowed")
        }
        return super.loadClass(className)
    }
}
