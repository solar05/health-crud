checks: check-format check-style check-namespaces

check-format:
	lein cljfmt check

fix-format:
	lein cljfmt fix

check-style:
	lein kibit

check-namespaces:
	lein eastwood

tests:
	lein trampoline test
