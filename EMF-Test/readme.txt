Data Uniformization Server => DUS

Query = Query-Protocol + Query-Text + Load-Protocol

Custom Load-Protocol:
	"addresses", "groups.id"		//addresses(load all attributes in the list), groups(load only id list)
	"pass=lazy", "groups=eager"		//pass(don't load password), groups(load all groups attributes)