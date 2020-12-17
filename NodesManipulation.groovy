import org.apache.jackrabbit.oak.spi.commit.CommitInfo
import org.apache.jackrabbit.oak.spi.commit.EmptyHook
import org.apache.jackrabbit.oak.spi.state.NodeStore
import org.apache.jackrabbit.oak.commons.PathUtils

def rmNode(def session, String path) {
    println "Removing node ${path}"

    NodeStore ns = session.store
    def nb = ns.root.builder()

    def aBuilder = nb
    for(p in PathUtils.elements(path)) {  aBuilder = aBuilder.getChildNode(p) }

    if(aBuilder.exists()) {
        rm = aBuilder.remove()
        ns.merge(nb, EmptyHook.INSTANCE, CommitInfo.EMPTY)
        return rm
    } else {
        println "Node ${path} doesn't exist"
        return false
    }
}

def addNode(def session, String path, String parentPath) {
    println "Adding node ${path}"

    NodeStore ns = session.store
    def nb = ns.root.builder()

    def aBuilder = nb
    for(p in PathUtils.elements(path)) {  aBuilder = aBuilder.getChildNode(parentPath) }

    if(aBuilder.exists()) {
        rm = aBuilder.setChildNode(path)
        ns.merge(nb, EmptyHook.INSTANCE, CommitInfo.EMPTY)
        return rm
    } else {
        println "Node ${path} doesn't exist"
        return false
    }
}