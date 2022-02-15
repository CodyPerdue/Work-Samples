--[[
    GD50
    Legend of Zelda

    Author: Colton Ogden
    cogden@cs50.harvard.edu
]]

GAME_OBJECT_DEFS = {
    ['switch'] = {
        type = 'switch',
        texture = 'switches',
        frame = 2,
        width = 16,
        height = 16,
        solid = false,
		consumable = false,
		onCollide = function() end,
		onConsume = function() end,
        defaultState = 'unpressed',
        states = {
            ['unpressed'] = {
                frame = 2
            },
            ['pressed'] = {
                frame = 1
            }
        }
    },
    ['pot'] = {
        type = 'pot',
		texture = 'pot',
		frame = 1,
		width = 16,
		height = 16,
		movementSpeed = 96,
		solid = true,
		consumable = false,
		onCollide = function(entity)
			if state == 'thrown' then
				entity:damage(1)
			end
		end,
		onConsume = function() end,
		fire = function(entity)

		end,
		defaultState = 'intact',
		states = {
			['intact'] = {}
		}
    },
	['heart'] = {
		type = 'heart',
		texture = 'hearts',
		frame = 5,
		width = 16,
		height = 16,
		solid = false,
		consumable = true,
		onCollide = function() end,
		onConsume = function(player)
			gSounds['heart']:play()
			player:heal(2)
		end,
		defaultState = 'exists',
		states = {
			['exists'] = {
                consumable = true
            }
		}
	}
}
