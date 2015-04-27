const blocks = (function() {
	var blocks;
	var list = minecraft.listBlocks();
	for (int i = 0; i < list.length; i++)
	{
		blocks[propNameFromMCID(list[i].id())] = list[i];
	}
	return blocks;
})();
const items = (function() {
	var items;
	var list = minecraft.listItems();
	for (int i = 0; i < list.length; i++)
	{
		items[propNameFromMCID(list[i].id())] = list[i];
	}
	return items;
})();

const propNameFromMCID = function (id)
{
	if (id.indexOf("minecraft" == 0))
	{
		id = id.substr(id.lastIndexOf(":") + 1);
	}
	else
	{
		id = id.replace(":","$");
	}
	return id;
};

const overworld = worlds.get(0);
const nether = worlds.get(-1);
const theend = worlds.get(1);

const isRemote = minecraft.isRemote();

const chat = function (msg)
{
	minecraft.sendChat(msg);
};